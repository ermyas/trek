var puzzleListTemplate = '{{ #puzzles }}<a href="play.html?pid={{id}}&z=4&lt=-34&ln=135" class="grey-text text-darken-2 collection-item">{{ name }}</a>{{/puzzles}}'

function loadUser() {
    console.log("loading user");
    console.log(puzzleListTemplate);
    $.getJSON("../puzzles.json", function (data) {
            console.log(data);
            var rendered = Mustache.render(puzzleListTemplate, data);
            $('#target').html(rendered);
        }
    );
}


window.onload = function () {
    loadUser();
};
