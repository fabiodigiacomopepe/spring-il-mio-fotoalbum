// Setto URL
const apiUrl = 'http://localhost:8080/api/v1/photos';

// Seleziono root dove iniettare template
const root = document.getElementById("root");

// Seleziono tasto cerca e input di ricerca
const submit = document.getElementById('tasto_cerca');
const input = document.getElementById('search');

// Dichiaro variabili da riempire con template literal successivamente
let contentAlbum;
let contentSinglePhoto;
let infoPhoto;

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
    if (!element.visible) {
        infoPhoto = "";
    } else {
        infoPhoto = `
        <div class="card" style="width: calc(100% / 3 - 15px); height: 380px;">
            <img src="${element.url}" class="card-img-top" alt="${element.title}"
                style="max-height: 200px; object-fit: cover;">
            <div class="card-body">
                <h5 class="card-title">${element.title}</h5>
                <p class="card-text" style="overflow-y: auto; height: 60px;">${element.description}</p>
            </div>
        </div>`;
    }
    return infoPhoto;
};

// Al click del pulsante cerca richiedo lista foto ma passando anche parametro di ricerca
submit.addEventListener('click', (event) => {
    event.preventDefault();
    getPhotos(input.value);
});

// Ritorno lista foto
getPhotos();