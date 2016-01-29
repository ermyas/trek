var puzzleListTemplate = '{{ #puzzles }}<a href="play.html?pid={{id}}&z=4&lt=-34&ln=135" class="grey-text text-darken-2 collection-item">{{ name }}</a>{{/puzzles}}'

function loadPuzzles(result) {
    console.log(result);
    $.getJSON("../puzzles.json", function (data) {
            console.log(data);
            var rendered = Mustache.render(puzzleListTemplate, data);
            $('#target').html(rendered);
        }
    );
}


window.onload = function () {
    loadPuzzles();
    client.getPuzzleList(3, 0, loadPuzzles)
};
