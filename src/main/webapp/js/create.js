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
            $("ul.universe." + universeId).append('<li>'+data.name+'</li>')
        });
}

$('button.delete').on('click', remove);
function remove(){
const id = this.id;

    fetch('../api/pops/'+ id, {
        
        method: 'DELETE'
    })
    .then(json => {
            $("li.pop." + id).remove()
    });
}