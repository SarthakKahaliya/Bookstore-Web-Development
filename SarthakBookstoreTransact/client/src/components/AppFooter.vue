<template>
  <footer class="container">
    <section class="links">
      <router-link to="/">about</router-link>
      <router-link to="/">contact</router-link>
      <router-link to="/">directions</router-link>
    </section>
    <section class="copyright">
      <i class="far fa-copyright">Book Buggz</i>
    </section>
    <section class="social-media-icons">
      <router-link class="button fab fa-facebook-f" to="/">acebook</router-link>
      <router-link class="button fab fa-twitter" to="/">Twitter</router-link>
    </section>
  </footer>
</template>

<script>
export default {
  name: "AppFooter",
};
</script>

<style scoped>
footer.container {
  background-color: white;
  display: flex;
  justify-content: space-between;
  position: fixed;
  bottom: 0em;
  margin: 0 auto;
  max-width: 1120px;
  min-width: 550px;
  width: 100%;
}
section {
  padding-bottom: 0;
}

.links,
.social-media-icons {
  display: flex;
  gap: 0.5em;
}

.links {
  padding-top: 0.5em;
  padding-left: 0.5em;
}

.button.fab.fa-facebook-f,
.button.fab.fa-twitter {
  padding-left: 0;
}

section.copyright {
  padding-top: 0.5em;
  text-align: center;
  color: var(--primary-color-dark);
}

.social-media-icons {
  justify-content: right;
  gap: 0.25em;
}
</style>
