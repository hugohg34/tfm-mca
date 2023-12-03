<template>
    <div>
      {{ dateTime }}
    </div>
  </template>
  
  <script>
  export default {
    name: 'DateRack',
    data() {
      return {
        dateTime: ''
      };
    },
    mounted() {
      this.updateDateTime();
      setInterval(this.updateDateTime, 1000);
    },
    methods: {
      updateDateTime() {
        const days = ['Sunday', 'Monday', 'Tuesday', 'Wednesday', 'Thursday', 'Friday', 'Saturday'];
        const now = new Date();
        const dayOfWeek = days[now.getDay()];
        const day = now.getDate().toString().padStart(2, '0');
        const month = (now.getMonth() + 1).toString().padStart(2, '0');
        const year = now.getFullYear();
        const hours = now.getHours().toString().padStart(2, '0');
        const minutes = now.getMinutes().toString().padStart(2, '0');
        const seconds = now.getSeconds().toString().padStart(2, '0');
        this.dateTime = `${dayOfWeek} ${day}/${month}/${year} ${hours}:${minutes}:${seconds}`;
      }
    }
  };
  </script>
  