<template>
    <div class="container">
        <span>았!! 북마크를 다시 되돌리시겠어요 ?</span>
        <button class="action" @click.stop="clicked">CLICK ME</button>
    </div>
</template>

<script>
export default {
    methods: {
        clicked() {
            alert('Clicked!');
        },
    },
};
</script>

<style></style>
