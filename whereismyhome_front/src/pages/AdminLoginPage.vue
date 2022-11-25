<template>
    <div>
        <header-component></header-component>
        <AdminLoginComponent></AdminLoginComponent>
    </div>
</template>

<script>
import HeaderComponent from '../components/common/HeaderComponent.vue';
import AdminLoginComponent from '../components/AdminLoginComponent.vue';

export default {
    components: { HeaderComponent, AdminLoginComponent },
};
</script>

<style></style>
