function loadUser() {
  console.log("loading user");
  var template = $('#template').html();
console.log(template);
  var data = $.getJSON("../puzzles.json", function (data) {
	
	console.log(data);
  var rendered = Mustache.render(template, data);
  $('#target').html(rendered);
}
);
}

window.onload = function() {
  loadUser();
};
