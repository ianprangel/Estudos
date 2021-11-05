let spaceship = prompt(`Qual o nome de sua Nave ?`);
let dobra = null;
let answerEntry = null;

if (spaceship !== null && spaceship !== `` && spaceship !== undefined) {
    answerEntry = prompt(`Deseja entrar em uma dobra espacial? \n\n1 - Sim \n2 - Não`)
    rotina (answerEntry)
} else {
    alert(`Para darmos continuidade é necessario informar o nome de sua nave!`);
}

function rotina (answerEntry) {
    if (answerEntry == 1 || answerEntry == `sim` || answerEntry == `Sim` || answerEntry == `SIM`) {
        dobra++
        while( answerEntry == 1 || answerEntry == `sim` || answerEntry == `Sim` || answerEntry == `SIM`){
            answerEntry = prompt(`Deseja entrar na próxima dobra espacial? \n\n1 - Sim \n2 - Não`);
            dobra++; 
        }
        alert(`Você utilizará a nave ${spaceship} para realizar ${dobra} dobras espaciais.`);
    } else {
        alert(`Você não utilizará a nave ${spaceship} para realizar dobras espaciais.`);
    }
}