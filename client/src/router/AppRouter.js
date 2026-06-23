import { createRouter, createWebHistory } from "vue-router";

const routes = [
  { path: "/", component: () => import("@/App.vue") },
  { path: "/login", component: () => import("@/views/Login.vue") },
  { path: "/signUp", component: () => import("@/views/SignUp.vue") },
];

const router = createRouter({
  history: createWebHistory(),
  routes,
});

export default router;