var botaoAdicionar = document.querySelector("#adicionar-paciente");
botaoAdicionar.addEventListener("click", function(event) {
    event.preventDefault();

    var form = document.querySelector("#form-adiciona");
    var paciente = obtemPacienteDoFormulario(form);
    
    var pacienteTr = montaTr(paciente);
    
    var erros = validaPaciente(paciente);

    if(erros.length > 0){
        exibeMensagensDeErro(erros);

        return;
    }
    

    var tabela = document.querySelector("#tabela-pacientes");

    tabela.appendChild(pacienteTr);

    form.reset(); 
    var mensagensErro = document.querySelector("#mensagem-erro");
    mensagensErro.innerHTML = "";

});

function obtemPacienteDoFormulario(form){

    var paciente = {
        nome: form.nome.value,
        peso: form.peso.value,
        altura: form.peso.value,
        gordura: form.gordura.value,
        imc: calculaImc(form.peso.value, form.altura.value)
    }
    
    return paciente;
}

function montaTr(paciente){
    var pacienteTr = document.createElement("tr");
    pacienteTr.classList.add("paciente");

    paciente.appendChild(montaTd(paciente.nome, "info-nome"));
    paciente.appendChild(montaTd(paciente.peso, "info-peso"));
    paciente.appendChild(montaTd(paciente.altura, "info-altura"));
    paciente.appendChild(montaTd(paciente.gordura, "info-gordura"));
    paciente.appendChild(montaTd(paciente.imc, "info-imc"));

    return pacienteTr;
}

function montaTd(dado, classe){
    var td = document.createElement("td");
    td.textContent = dado;
    td.classList.add(classe);

    return td;
}

function validaPaciente(paciente){

    var erros = [];

    if(paciente.nome.length < 2) {
        erros.push("Nome inválido!");
    }

    if(!validaPeso(paciente.peso)) {
        erros.push("Peso inválido!");
    }

    if(!validaAltura(paciente.altura)) {
        erros.push("Altura inválida!");
    }

    if(paciente.gordura.length == 0){
        erros.push("A gordura não pode ser em branco");
    }

    if(paciente.peso.length == 0){
        erros.push("O peso não pode ficar em branco");
    }

    if(paciente.altura.length == 0) {
        erros.push("A altura não pode ficar em branco");
    }

    return erros;
}

function exibeMensagensDeErro(erros) {
    var ul = document.querySelector("#mensagem-erro");
    ul.innerHTML= "";

    erros.forEach(function(erro){
        var li = document.createElement("li");
        li.textContent = erro;
        ul.appendChild(li);
    });
}