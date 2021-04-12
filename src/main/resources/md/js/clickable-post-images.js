/**
 * Make all post's images on the main page clickable. They will link to the post's page.
 */
document.querySelectorAll(".post img").forEach(el => {
  const postUrl = el.closest(".post").querySelector("a.post-title-link").getAttribute("href");
  el.onclick = () => {
    window.location = postUrl;
  };
  el.classList.add("post-img");
});
