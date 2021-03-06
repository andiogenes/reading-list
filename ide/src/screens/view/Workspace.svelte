<script lang="ts">
    import { makeDnD } from "../common/dnd-util";
    import { widgetsByName } from "./widgets/widgets";
    import Pane from "./Pane.svelte";
    import { state } from "../common/global-state";

    export let view;

    let viewportOffsetX: number = 0;
    let viewportOffsetY: number = 0;

    const { onMouseDown, onMouseUp, onMouseMove } = makeDnD((e) => {
        viewportOffsetX += e.movementX;
        viewportOffsetY += e.movementY;
    });

    const screen = { width: window.innerWidth, height: window.innerHeight };
    const pane = { width: view.pane.width, height: view.pane.height };

    $: viewportOffsetX = (screen.width - pane.width) / 2;
    $: viewportOffsetY = (screen.height - pane.height) / 2;

    window.addEventListener("resize", (_) => {
        screen.width = window.innerWidth;
        screen.height = window.innerHeight;
    });

    let widgets: any[] = view.widgets.map((v, i, a) => ({
        type: v.type,
        caption: v.caption,
        x: v.x,
        y: v.y,
        onclick: v.onclick,
        oninput: v.oninput,
        component: widgetsByName[v.type],
        deleteAction: (i) => {
            widgets.splice(i, 1);
            widgets = widgets;
            setCurrentWidget(null);
        },
    }));

    $: {
        state.update((v) => {
            if (!v.view) {
                v.view = {};
            }
            v.view.pane = pane;
            v.view.widgets = widgets;
            return v;
        });
    }

    function addWidget(name: string, e: DragEvent) {
        widgets.push({
            type: name,
            caption: name,
            x: e.clientX - viewportOffsetX,
            y: e.clientY - viewportOffsetY,
            component: widgetsByName[name],
            onclick: "",
            oninput: "",
            deleteAction: (i) => {
                widgets.splice(i, 1);
                widgets = widgets;
                setCurrentWidget(null);
            },
        });

        widgets = widgets;
    }

    export let currentWidget;

    function setCurrentWidget(w: any) {
        currentWidget = w;
    }
</script>

<svelte:window on:mouseup={onMouseUp} />

<div
    on:mousedown={(e) => {
        setCurrentWidget(null);
        onMouseDown(e);
    }}
    on:mousemove={onMouseMove}
    class="fixed w-screen h-screen bg-gray-100"
/>
<Pane
    {addWidget}
    {setCurrentWidget}
    bind:width={pane.width}
    bind:height={pane.height}
    bind:viewportOffsetX
    bind:viewportOffsetY
/>

{#each widgets as widget, i}
    <svelte:component
        this={widget.component}
        bind:caption={widget.caption}
        deleteAction={() => widget.deleteAction(i)}
        {setCurrentWidget}
        bind:x={widget.x}
        bind:y={widget.y}
        bind:oninp={widget.oninput}
        bind:onclk={widget.onclick}
        bind:viewportOffsetX
        bind:viewportOffsetY
    />
{/each}
