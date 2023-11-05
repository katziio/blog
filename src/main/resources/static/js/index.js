var checkLists = document.getElementsByClassName("dropdown-check-list");

Array.from(checkLists).forEach(function (checkList) {
  var anchor = checkList.getElementsByClassName("anchor")[0];
  var items = checkList.getElementsByClassName("items")[0];
  var searchInput = document.createElement("input");
  searchInput.setAttribute("type", "text");
  searchInput.setAttribute("placeholder", "Search");
  searchInput.addEventListener("input", filterItems);

  anchor.appendChild(searchInput);

  anchor.onclick = function (evt) {
    if (checkList.classList.contains("visible")) {
      checkList.classList.remove("visible");
    } else {
      checkList.classList.add("visible");
    }
  };

  function filterItems() {
    var input, filter, ul, li, a, i;
    input = searchInput;
    filter = input.value.toUpperCase();
    ul = items;
    li = ul.getElementsByTagName("li");

    for (i = 0; i < li.length; i++) {
      a = li[i];
      txtValue = a.textContent || a.innerText;
      if (txtValue.toUpperCase().indexOf(filter) > -1) {
        a.style.display = "";
      } else {
        a.style.display = "none";
      }
    }
  }
});
