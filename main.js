'use strict';
document.addEventListener('DOMContentLoaded', function() {
    var botaoInscrever = document.querySelector('.modal-cadastro');
    var botaoEntrar = document.querySelector('.modal-login');
    var quadrado = document.getElementById('quadrado');
    var modalCadastro = document.getElementById('modal-cadastro');
    var modalLogin = document.getElementById('modal-login');
    var botaoFechar = document.querySelector('.close');

    function mostrarModal(modalId) {
        // Esconde todos os modais
        modalCadastro.style.display = 'none';
        modalLogin.style.display = 'none';

        // Mostra o modal especificado
        document.getElementById(modalId).style.display = 'block';

        // Mostra o quadrado
        quadrado.style.display = 'block';
    }

    function fecharQuadrado() {
        // Esconde o quadrado
        quadrado.style.display = 'none';
    }

    botaoInscrever.addEventListener('click', function() {
        mostrarModal('modal-cadastro');
    });

    botaoEntrar.addEventListener('click', function() {
        mostrarModal('modal-login');
    });

    var botoesFechar = document.querySelectorAll('.close');

    // Função para fechar o modal
    function fecharModal() {
        // Esconde o quadrado
        quadrado.style.display = 'none';
    }

    // Adiciona ouvintes de eventos a todos os botões de fechar
    botoesFechar.forEach(function(botao) {
        botao.addEventListener('click', fecharModal);
    });


    
    var formCadastro = document.getElementById('cadastro');
    var formLogin = document.getElementById('login');

    formCadastro.addEventListener('submit', function(event) {
        event.preventDefault();
        var email = document.querySelector('#modal-cadastro #email').value; // Seleciona o campo de e-mail dentro do modal de cadastro
        var senha = document.querySelector('#modal-cadastro #senha').value; // Seleciona o campo de senha dentro do modal de cadastro

        fetch('/inscrever', {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json',
            },
            body: JSON.stringify({ email: email, password: senha }),
        })
        .then(response => response.json())
        .then(data => console.log(data))
        .catch((error) => {
            console.error('Error:', error);
        });

        // Fecha o quadrado após o envio do formulário
        fecharQuadrado();

    })

    formLogin.addEventListener('submit', function(event) { // Adiciona ouvinte de evento ao formulário de login
        event.preventDefault();
        var email = document.querySelector('#modal-login #email').value; // Seleciona o campo de e-mail dentro do modal de login
        var senha = document.querySelector('#modal-login #senha').value; // Seleciona o campo de senha dentro do modal de login

        fetch('/login', {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json',
            },
            body: JSON.stringify({ email: email, password: senha }),
        })
        .then(response => response.json())
        .then(data => console.log(data))
        .catch((error) => {
            console.error('Error:', error);
        });

        // Fecha o quadrado após o envio do formulário
        fecharQuadrado();
    });
});



var card = document.getElementById('card');
var cardInner = card.querySelector('.flip-card-inner');
var swipeLeft = document.getElementById('swipeLeft');
var swipeRight = document.getElementById('swipeRight');

swipeLeft.addEventListener('click', function(event) {
    event.stopPropagation();
    var moveOutWidth = document.body.clientWidth;
    cardInner.style.transform = 'translate(-' + moveOutWidth + 'px) rotateY(0deg)';
    setTimeout(function() {
        card.remove();
        addNewCard();
    }, 300);
});

swipeRight.addEventListener('click', function(event) {
    event.stopPropagation();
    var moveOutWidth = document.body.clientWidth ;
    cardInner.style.transform = 'translate(' + moveOutWidth + 'px) rotateY(0deg)';
    setTimeout(function() {
        card.remove();
        addNewCard();
    }, 300);
});



card.addEventListener('click', function() {
    if (cardInner.style.transform === 'rotateY(180deg)') {
        cardInner.style.transform = 'rotateY(0deg)';
    } else {
        cardInner.style.transform = 'rotateY(180deg)';
    }
});



function addNewCard() {
    var newCard = document.createElement('div');
    newCard.classList.add('flip-card');
    newCard.innerHTML = `
        <div class="flip-card-inner">
            <div class="flip-card-front">
                <h1>Hello World!</h1>
                <div class="tinder--buttons">
                    <button class="swipeLeft" style="background-color: red;"><i class="fa fa-arrow-left"></i></button>
                    <button class="swipeRight" style="background-color: green;"><i class="fa fa-arrow-right"></i></button>
                </div>
            </div>
            <div class="flip-card-back">
                <h1>Oi Mundo!</h1>
                <p>Architect & Engineer</p>
                <p>We love that guy</p>
            </div>
        </div>
    `;
    document.querySelector('.flip-background').appendChild(newCard);

    var newCardInner = newCard.querySelector('.flip-card-inner');
    var newSwipeLeft = newCard.querySelector('.swipeLeft');
    var newSwipeRight = newCard.querySelector('.swipeRight');

    newSwipeLeft.addEventListener('click', function(event) {
        event.stopPropagation();
        var moveOutWidth = document.body.clientWidth;
        newCardInner.style.transform = 'translate(-' + moveOutWidth + 'px) rotateY(0deg)';
        setTimeout(function() {
            newCard.remove();
            addNewCard();
        }, 300);
    });

    newSwipeRight.addEventListener('click', function(event) {
        event.stopPropagation();
        var moveOutWidth = document.body.clientWidth;
        newCardInner.style.transform = 'translate(' + moveOutWidth + 'px) rotateY(0deg)';
        setTimeout(function() {
            newCard.remove();
            addNewCard();
        }, 300);
    });

    newCard.addEventListener('click', function() {
        if (newCardInner.style.transform === 'rotateY(180deg)') {
            newCardInner.style.transform = 'rotateY(0deg)';
        } else {
            newCardInner.style.transform = 'rotateY(180deg)';
        }
    });


    



    setTimeout(function() {
        newCard.style.opacity = '1';
        newCard.style.transform = 'translateY(0)';
    }, 50);
    
}
