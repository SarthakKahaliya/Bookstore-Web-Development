<template>
  <div class="notfound-page">
    <div class="invalid">404</div>
    <div class="text-invalid">
      The Page you were looking found was not found
    </div>
    <router-link to="/" tag="button">Home Page</router-link>
  </div>
</template>

<script>
export default {
  name: "NotFound",
};
</script>

<style scoped>
.invalid {
  font-size: 100px;
  color: var(--primary-color-dark);
}
.notfound-page {
  margin-top: 5em;
  text-align: center;
  padding: 5em;
  background-color: oldlace;
  border: none;
  border-radius: 20px;
}
.text-invalid {
  font-size: 20px;
}
.notfound-page > button {
  margin-top: 20px;
  margin-bottom: 20px;
  border: none;
  color: var(--primary-color);
  min-width: 180px;
  height: 40px;
  font-size: 16px;
  background-color: transparent;
  border: solid;
  border-color: var(--primary-color);
  font-weight: bold;
  cursor: pointer;
  border-radius: 8px;
}
.notfound-page > button:hover {
  background-color: var(--primary-color-dark);
  border: solid;
  border-color: var(--primary-color-dark);
  color: white;
}
</style>
