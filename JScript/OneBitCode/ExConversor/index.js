let lyDistance = prompt("Digite a distância em anos-luz:");
let conversionOptions = prompt("Escolha uma das opções de unidade para conversão:\n\n1 - Parsec (pc)\n2 - Unidade Astronômica (AU)\n3 - Quilômetros (km)\n");

let chosenConversion;
let convertedValue;
switch(conversionOptions){
    case "1":
        chosenConversion = "Parsec (pc)"
        convertedValue = lyDistance * 0.306601
        break
    case "2":
        chosenConversion = "Unidade Astronômica (AU)"
        convertedValue = lyDistance * 63241.1
        break
    case "3":
        chosenConversion = "Quilômetros (km)"
        convertedValue = lyDistance * (9.5 * Math.pow(10,12))
        break
}

if(conversionOptions > 0 && conversionOptions <= 3) {
    alert("Distância em Anos-luz: " + lyDistance + "\nDistância convertida para " + chosenConversion + ": " + convertedValue)
} else {
    alert("Distância em Anos-luz: " + lyDistance + "\nUnidade não identificada: Conversão fora do escopo")
}