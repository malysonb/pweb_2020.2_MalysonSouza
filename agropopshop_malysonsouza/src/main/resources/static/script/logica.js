

function mudar(){
    var valor = document.getElementById("clientes")
    var temp = document.getElementsByName("adicionar")
    temp.forEach(a => {
        a.setAttribute("href", valor.value)
    });
    
}