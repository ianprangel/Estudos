let dataPartida = prompt("Digite a data de partida (formato DD/MM/YYYY");
let dataPartidaModificada = moment(dataPartida, "DD/MM/YYYY");
let hoje = moment();

let dataDiferenca = hoje - dataPartidaModificada;

let opcao = prompt("Escolha como gostaria de exibir o tempo de partida\n1- Segundos\n2- Minutos\n3- Horas\n4- Dias");

if(opcao == '1') {
    let segundos = Math.round(dataDiferenca / 1000);
    alert("Tempo de vôo: " + segundos + " segundos");
} else if (opcao == '2' ){
    let minutos = Math.round(dataDiferenca / 1000 / 60);
    alert("Tempo de vôo: " + minutos + " minutos");
} else if (opcao == '3' ){
    let horas = Math.round(dataDiferenca / 1000 / 3600);
    alert("Tempo de vôo: " + horas + " horas");
} else if (opcao == '4' ){
    let dias = Math.round(dataDiferenca / 1000 / 3600 / 24);
    alert("Tempo de vôo: " + dias + " dias");
} else {
    alert ("Opção inválida");
}
