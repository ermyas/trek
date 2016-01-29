var puzzleListTemplate = '{{ #puzzles }}<a href="play.html?pid={{id}}&z=4&lt=-34&ln=135" class="grey-text text-darken-2 collection-item">{{ owner }}</a>{{/puzzles}}'

function loadPuzzles(result) {
    var data = {};
    data.puzzles = result;
    var rendered = Mustache.render(puzzleListTemplate, data);
    $('#target').html(rendered);
}


window.onload = function () {
    client.getPuzzleList(3, 0, loadPuzzles)
};
