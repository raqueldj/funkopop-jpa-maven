$('button.create').on('click', create);

function create() {
    const universeId = $('select').val();

    const data = {
        name: $('input[name=name]').val(),
        universe: {
            id: universeId
        }
    }

    //sert a faire une requete
    fetch('../api/pops', {
        headers: {
            'Accept': 'application/json',
            'Content-Type': 'application/json'
        },
        method: 'POST',
        body: JSON.stringify(data)
    })
        .then(resp => resp.json())
        .then(json => {
            $("ul.universe." + universeId).append($('<li>' + json.id + ' - ' + json.name + ' <button type="button" class="delete" id="' + json.id + '">Delete ' + json.name + '</button></li>'));
            $('button.delete#' + json.id).on('click', remove);
        });

        $('input[name=name]').val("");
}

$('button.delete').on('click', remove);
function remove(event) {
    const button = $(event.target);
    const id = this.id;

    fetch('../api/pops/' + id, {
        method: 'DELETE'
    })
        .then(json => {
            button.closest("li").remove()
        });
}
