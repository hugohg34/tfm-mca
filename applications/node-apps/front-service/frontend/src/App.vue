<template>
  <h1>Rack</h1>
  <EstablishmentRack msg="Rack"/>
  <DateRack msg="Date"/>
</template>

<script>
import DateRack from './components/DateRack.vue';
import EstablishmentRack from './components/EstablishmentRack.vue'

export default {
  name: 'App',
  components: {
    EstablishmentRack,
    DateRack
}
}
</script>

<style>
#app {
  font-family: Avenir, Helvetica, Arial, sans-serif;
  -webkit-font-smoothing: antialiased;
  -moz-osx-font-smoothing: grayscale;
  text-align: center;
  color: #2c3e50;
  margin-top: 60px;
}
</style>
