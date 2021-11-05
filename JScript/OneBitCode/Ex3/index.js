
let nome = window.prompt("Seja Bem Vindo Piloto, informe o seu nome:");
let velInicial = 0;
let velNave = prompt("A velocidade da nave é " + velInicial + "km/s. Qual velocidade deseja ir?");
let velConfirm = confirm("Confirme que a velocidade desejada é "+ velNave +" km/s, se quiser atualizar a velocidade!") ;

if(velNave <= 0){
    window.alert("Nave está parada. Considere partir e aumentar a velocidade."); 
}else if(velNave < 40){
    window.alert("Você está devagar, podemos aumentar mais.");
}else if(velNave >= 40 && velNave < 80){
    window.alert("Parece uma boa velocidade para manter.");
}else if(velNave >= 80 && velNave < 100){
    window.alert("Velocidade alta. Considere diminuir.");
}else if(velNave >= 100){
    window.alert("Velocidade perigosa. Controle automático forçado!!!");
}

window.alert("Piloto: "+ nome + "\nVelocidade atual " + velNave + "km/s.");