// Setto URL
const apiUrl = 'http://localhost:8080/api/v1/photos';

// Seleziono root dove iniettare template
const root = document.getElementById("root");

// Seleziono tasto cerca e input di ricerca
const submit = document.getElementById('button_search');
const input = document.getElementById('search');

// Dichiaro variabili da riempire con template literal successivamente
let contentAlbum;
let contentSinglePhoto;
let infoPhoto1;
let infoPhoto2;
let infoPhoto3;
let infoPhotoFinal;

// Ritorno lista di foto per metterle in pagina
const getPhotos = async (search) => {
    // Se parametro di ricerca non è stato passato lo setto come stringa vuota
    if (search == undefined) {
        search = "";    // Risultato: ?search=""
    }
    try {
        let response = await axios.get(apiUrl + '?search=' + search);
        renderPhotoList(response.data);
    } catch (e) {
        console.log(e);
    }
};

// Ciclo su ogni foto e chiamo metodo per creare template literal
const renderPhotoList = (data) => {
    contentAlbum = "";
    if (data.length > 0) {
        data.forEach((element) => {
            contentSinglePhoto = renderPhoto(element);
            contentAlbum += contentSinglePhoto;
        });
    } else {
        contentAlbum = '<div class="alert alert-info" style="padding: 30px;">Nessuna foto presente in lista.</div>';
    }
    root.innerHTML = contentAlbum;
};

// Creo template literal per ogni foto e lo inietto in pagina
function renderPhoto(element) {
    infoPhoto1 = `<div class="card" style="width: calc(100% / 3 - 15px); height: 380px;">`;
    infoPhoto2 = `<img src="http://localhost:8080/files/cover/${element.id}" onerror="this.src='${element.url}';" class="card-img-top" alt="${element.title}" style="max-height: 200px; object-fit: cover;">`
    infoPhoto3 = `
        <div class="card-body">
            <h5 class="card-title">${element.title}</h5>
            <p class="card-text" style="overflow-y: auto; height: 60px;">${element.description}</p>
        </div>
    </div>`;
    infoPhotoFinal = infoPhoto1 + infoPhoto2 + infoPhoto3;
    return infoPhotoFinal;
};

// Al click del pulsante cerca richiedo lista foto ma passando anche parametro di ricerca
submit.addEventListener('click', (event) => {
    event.preventDefault();
    getPhotos(input.value);
});

// Ritorno lista foto
getPhotos();

// Seleziono FORM
const form = document.getElementById('formContact');

// Funzione che parte al click sul pulsante INVIA
form.addEventListener('submit', async function (event) {
    // Disattivo comportamento default del pulsante
    event.preventDefault();

    // Prendo i dati dal form e li salvo in delle variabili
    const formData = new FormData(form);
    const emailForm = formData.get('emailForm');
    const bodyEmail = formData.get('bodyEmail');

    if (validateForm()) {
        // Trasformo tutto in JSON
        const jsonData = {
            senderEmail: emailForm,
            body: bodyEmail,
        };

        const config = {
            headers: {
                'Content-Type': 'application/json',
            },
        };

        // Invio al RestController su Java per creare nuova messaggio
        try {
            await axios.post(apiUrl + '/send', JSON.stringify(jsonData), config);
            window.location.href = '/front-end/index.html';
        } catch (error) {
            console.error(error);
        }
    }
});

function validateForm() {
    let isValid = true;

    // Validazione del campo "emailForm"
    const emailValue = emailForm.value.trim();
    if (emailValue === "") {
        isValid = false;
        document.getElementById("emailForm-error").innerHTML = "Il campo 'Email' è obbligatorio.";
        emailForm.classList.add("is-invalid");
    } else if (!isValidEmail(emailValue)) {
        isValid = false;
        document.getElementById("emailForm-error").innerHTML = "Inserisci un indirizzo email valido.";
        emailForm.classList.add("is-invalid");
    } else {
        document.getElementById("emailForm-error").innerHTML = "";
        emailForm.classList.remove("is-invalid");
    }

    // Validazione del campo "bodyEmail"
    const nameValue = bodyEmail.value.trim();
    if (nameValue === "") {
        isValid = false;
        document.getElementById("bodyEmail-error").innerHTML = "Il campo 'Messaggio' è obbligatorio.";
        bodyEmail.classList.add("is-invalid");
    }
    else if (nameValue.length < 3) {
        isValid = false;
        document.getElementById("bodyEmail-error").innerHTML = "Il campo 'Messaggio' deve contenere almeno 3 caratteri";
        bodyEmail.classList.add("is-invalid");
    }
    else {
        document.getElementById("bodyEmail-error").innerHTML = "";
        bodyEmail.classList.remove("is-invalid");
    }

    return isValid;
}

function isValidEmail(email) {
    console.log("sdfgsd");
    const emailRegex = /^[^\s@]+@[^\s@]+\.[^\s@]+$/;
    return emailRegex.test(email);
}