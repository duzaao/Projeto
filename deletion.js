// Função para criar e exibir os cards
function createAndDisplayCards(data) {
    // Limpa o conteúdo anterior
    document.getElementById('cardContainer').innerHTML = '';

    // Loop sobre os dados para criar e exibir as cartas
    data.forEach(card => {
        const cardElement = document.createElement('div');
        cardElement.classList.add('flip-card');

        cardElement.innerHTML = `
            <div class="flip-card-inner">
                <div class="flip-card-front">
                    <h1>${card.titleFront}</h1>
                    <p>${card.messageFront}</p>
                </div>
                <div class="flip-card-back">
                    <h1>${card.titleBack}</h1>
                    <p>${card.messageBack}</p>
                </div>
            </div>
        `;

        // Adiciona um evento de clique para a carta
        cardElement.addEventListener('click', function() {
            deleteCard(card.id); // Chama a função para excluir a carta
        });

        // Adiciona a carta ao contêiner de cartas
        document.getElementById('cardContainer').appendChild(cardElement);
    });
}

// Função para deletar um card
function deleteCard(cardId) {
    var userId = localStorage.getItem('userId');

    // Verificar se o userId foi obtido corretamente
    if (!userId) {
        console.error('Erro: userId não encontrado no localStorage');
        return;
    }

    // Construir a URL com o userId
    var url = 'http://localhost:8080/users/' + userId + '/notes';

    // Fazer o fetch para deletar a nota com o cardId especificado
    fetch(`${url}/${cardId}`, {
        method: 'DELETE',
    })
    .then(response => {
        if (!response.ok) {
            throw new Error('Erro ao excluir a carta');
        }
        console.log('Carta excluída com sucesso');
        // Atualize as cartas após a exclusão
        fetchCardsAndUpdate();
    })
    .catch(error => {
        console.error('Erro:', error);
    });
}

// Função para buscar e exibir os cards
function fetchCardsAndUpdate() {
    var userId = localStorage.getItem('userId');

    // Verificar se o userId foi obtido corretamente
    if (!userId) {
        console.error('Erro: userId não encontrado no localStorage');
        return;
    }

    // Construir a URL com o userId
    var url = 'http://localhost:8080/users/' + userId + '/notes';
    fetch(url)
    .then(response => {
        if (!response.ok) {
            throw new Error('Erro ao obter os dados');
        }
        return response.json();
    })
    .then(data => {
        createAndDisplayCards(data);
    })
    .catch(error => {
        console.error('Erro:', error);
    });
}

// Chama a função para buscar e exibir os cards inicialmente
fetchCardsAndUpdate();

// Função para filtrar os cards pelo título
function filterCardsByTitle(title) {
    var userId = localStorage.getItem('userId');

    // Verificar se o userId foi obtido corretamente
    if (!userId) {
        console.error('Erro: userId não encontrado no localStorage');
        return;
    }

    // Construir a URL com o userId
    var url = 'http://localhost:8080/users/' + userId + '/notes';
    fetch(url)
    .then(response => {
        if (!response.ok) {
            throw new Error('Erro ao obter os dados');
        }
        return response.json();
    })
    .then(data => {
        // Filtra os cards pelo título
        const filteredData = data.filter(card => card.titleFront.toLowerCase().includes(title.toLowerCase()));
        createAndDisplayCards(filteredData);
    })
    .catch(error => {
        console.error('Erro:', error);
    });
}

// Adiciona o evento de pesquisa ao campo de entrada
document.getElementById('searchInput').addEventListener('input', function(event) {
    const searchTitle = event.target.value;
    filterCardsByTitle(searchTitle);
});

document.getElementById('voltarButton').addEventListener('click', function() {
    window.location.href = 'oi.html'; // Redireciona para oi.html ao clicar no botão "Voltar"
});

document.addEventListener('DOMContentLoaded', function() {
    var logoutButton = document.getElementById('logoutButton');

    logoutButton.addEventListener('click', function() {
        // Exibe um aviso de confirmação
        var confirmLogout = confirm('Tem certeza que deseja sair?');

        // Se o usuário confirmar, redireciona para index.html
        if (confirmLogout) {
            localStorage.removeItem('userId');
            window.location.href = 'index.html';
        }
    });
});
