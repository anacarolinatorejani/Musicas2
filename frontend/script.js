const API_URL = "http://localhost:8080/musicas";

const formulario = document.getElementById("musicaForm");
const mensagem = document.getElementById("mensagem");
const resultado = document.getElementById("resultado");

let musicaAtual = null;

function mostrarMensagem(texto, tipo) {
    mensagem.textContent = texto;
    mensagem.className = tipo;
}

function limparFormulario() {
    formulario.reset();

    document.getElementById("id").value = "";
    document.getElementById("formTitle").textContent = "Cadastrar música";
    document.getElementById("btnSalvar").innerHTML = "<span>▣</span> Cadastrar música";
    document.getElementById("btnCancelar").hidden = true;

    musicaAtual = null;
}

function obterDadosFormulario() {
    return {
        nome: document.getElementById("nome").value.trim(),
        artista: document.getElementById("artista").value.trim(),
        genero: document.getElementById("genero").value.trim(),
        ano: document.getElementById("ano").value.trim()
    };
}

formulario.addEventListener("submit", async function(event) {
    event.preventDefault();

    const musica = obterDadosFormulario();
    const id = document.getElementById("id").value;

    try {
        if (id) {
            await atualizarMusica(id, musica);
        } else {
            await cadastrarMusica(musica);
        }
    } catch (erro) {
        mostrarMensagem(erro.message, "erro");
    }
});

async function cadastrarMusica(musica) {
    const resposta = await fetch(API_URL, {
        method: "POST",
        headers: {
            "Content-Type": "application/json"
        },
        body: JSON.stringify(musica)
    });

    if (!resposta.ok) {
        throw new Error("Não foi possível cadastrar a música.");
    }

    mostrarMensagem("Música cadastrada com sucesso!", "sucesso");
    limparFormulario();
}

async function buscarMusica() {
    const nome = document.getElementById("buscaNome").value.trim();

    if (!nome) {
        mostrarMensagem("Digite o nome de uma música.", "erro");
        return;
    }

    try {
        const resposta = await fetch(`${API_URL}/${encodeURIComponent(nome)}`);

        if (!resposta.ok) {
            throw new Error("Música não encontrada.");
        }

        const musica = await resposta.json();
        musicaAtual = musica;

        mostrarMusica(musica);
        mostrarMensagem("Música encontrada com sucesso!", "sucesso");

    } catch (erro) {
        resultado.innerHTML = '<p class="vazio"><span>♫</span> Música não encontrada. Confira o nome e tente novamente.</p>';
        mostrarMensagem(erro.message, "erro");
    }
}

function escaparHTML(valor) {
    const elemento = document.createElement("div");
    elemento.textContent = valor ?? "";
    return elemento.innerHTML;
}

function mostrarMusica(musica) {
    resultado.innerHTML = `
        <div class="musica">
            <div class="musica-capa" aria-hidden="true">♫</div>

            <div class="musica-info">
                <h3>${escaparHTML(musica.nome)}</h3>
                <p class="musica-artista">${escaparHTML(musica.artista)}</p>

                <div class="musica-detalhes">
                    <span>◇ <strong>Gênero:</strong> ${escaparHTML(musica.genero)}</span>
                    <span>▦ <strong>Ano:</strong> ${escaparHTML(musica.ano)}</span>
                    <span># <strong>ID:</strong> ${escaparHTML(musica.id)}</span>
                </div>
            </div>

            <div class="acoes">
                <button class="editar" id="btnEditar">✎ Editar</button>
                <button class="excluir" id="btnExcluir">▣ Excluir</button>
            </div>
        </div>
    `;

    document.getElementById("btnEditar").addEventListener("click", function() {
        prepararEdicao(musica);
    });

    document.getElementById("btnExcluir").addEventListener("click", function() {
        excluirMusica(musica.nome);
    });
}

function prepararEdicao(musica) {
    document.getElementById("id").value = musica.id;
    document.getElementById("nome").value = musica.nome;
    document.getElementById("artista").value = musica.artista;
    document.getElementById("genero").value = musica.genero;
    document.getElementById("ano").value = musica.ano;

    document.getElementById("formTitle").textContent = "Editar música";
    document.getElementById("btnSalvar").innerHTML = "<span>▣</span> Salvar alterações";
    document.getElementById("btnCancelar").hidden = false;

    mostrarMensagem("Edite os campos e salve as alterações.", "sucesso");

    formulario.scrollIntoView({ behavior: "smooth" });
}

async function atualizarMusica(id, musica) {
    const resposta = await fetch(`${API_URL}/${id}`, {
        method: "PUT",
        headers: {
            "Content-Type": "application/json"
        },
        body: JSON.stringify(musica)
    });

    if (!resposta.ok) {
        throw new Error("Não foi possível atualizar a música.");
    }

    mostrarMensagem("Música atualizada com sucesso!", "sucesso");

    limparFormulario();

    document.getElementById("buscaNome").value = musica.nome;

    await buscarMusica();
}

async function excluirMusica(nome) {
    const confirmar = confirm(`Deseja excluir a música "${nome}"?`);

    if (!confirmar) {
        return;
    }

    try {
        const resposta = await fetch(
            `${API_URL}/${encodeURIComponent(nome)}`,
            {
                method: "DELETE"
            }
        );

        if (!resposta.ok) {
            throw new Error("Não foi possível excluir a música.");
        }

        resultado.innerHTML = '<p class="vazio"><span>♫</span> Música excluída. Faça uma nova pesquisa.</p>';

        mostrarMensagem("Música excluída com sucesso!", "sucesso");

        limparFormulario();

    } catch (erro) {
        mostrarMensagem(erro.message, "erro");
    }
}

document.getElementById("btnBuscar").addEventListener("click", buscarMusica);

document.getElementById("buscaNome").addEventListener("keydown", function(event) {
    if (event.key === "Enter") {
        buscarMusica();
    }
});

document.getElementById("btnCancelar").addEventListener("click", limparFormulario);