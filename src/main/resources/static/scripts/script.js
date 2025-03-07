// Кнопка выхода в шапке header.html
document.addEventListener('DOMContentLoaded', function () {
    function logoutRequest() {
        const csrfToken = document.querySelector('meta[name="_csrf"]').content;
        const csrfHeader = document.querySelector('meta[name="_csrf_header"]').content;

        const form = document.createElement('form');
        form.method = 'POST';
        form.action = '/logout'; // Укажи URL для POST-запроса

        // Создаём скрытое поле для CSRF-токена
        const csrfInput = document.createElement('input');
        csrfInput.type = 'hidden';
        csrfInput.name = '_csrf'; // Имя CSRF-токена
        csrfInput.value = csrfToken; // Значение CSRF-токена
        form.appendChild(csrfInput);

        document.body.appendChild(form);
        form.submit();
    }
    // Делаем функцию глобальной, чтобы она была доступна в HTML
    window.logoutRequest = logoutRequest;
});

// Кнопка удаления задачи tasks.html
document.addEventListener('DOMContentLoaded', function () {
    function delRequest(taskId) {
        alert("DEL BUTTON")
        const url = `/tasks/${taskId}/del`; //"/tasks/{id}/del"

        const csrfToken = document.querySelector('meta[name="_csrf"]').content;
        const csrfHeader = document.querySelector('meta[name="_csrf_header"]').content;

        const form = document.createElement('form');
        form.method = 'POST';
        form.action = url; // Укажи URL для POST-запроса

        // Создаём скрытое поле для CSRF-токена
        const csrfInput = document.createElement('input');
        csrfInput.type = 'hidden';
        csrfInput.name = '_csrf'; // Имя CSRF-токена
        csrfInput.value = csrfToken; // Значение CSRF-токена
        form.appendChild(csrfInput);

        document.body.appendChild(form);
        form.submit();
    }
    // Делаем функцию глобальной, чтобы она была доступна в HTML
    window.delRequest = delRequest;
});

// Кнопка изменения задачи task.html
document.addEventListener('DOMContentLoaded', function () {
    function editRequest(taskId) {
        alert("EDIT BUTTON")
        const url = `/tasks/${taskId}/edit`; //"/tasks/{id}/del"

        const csrfToken = document.querySelector('meta[name="_csrf"]').content;
        const csrfHeader = document.querySelector('meta[name="_csrf_header"]').content;

        const form = document.createElement('form');
        form.method = 'GET';
        form.action = url; // Укажи URL для GET-запроса

        // Создаём скрытое поле для CSRF-токена
        const csrfInput = document.createElement('input');
        csrfInput.type = 'hidden';
        csrfInput.name = '_csrf'; // Имя CSRF-токена
        csrfInput.value = csrfToken; // Значение CSRF-токена
        form.appendChild(csrfInput);

        document.body.appendChild(form);
        form.submit();
    }
    // Делаем функцию глобальной, чтобы она была доступна в HTML
    window.editRequest = editRequest;
});


    // Находим все элементы с классом .nav-link
    const navLinks = document.querySelectorAll('.nav-link');

    // Проверяем, найдены ли элементы
    if (navLinks.length === 0) {
    console.error('Элементы с классом .nav-link не найдены!');
} else {
    console.log('Найдено элементов:', navLinks.length);
}

    // Добавляем обработчик события для каждого элемента
    navLinks.forEach(link => {
    link.addEventListener('click', function (e) {
        // Убираем класс "active" у всех элементов
        navLinks.forEach(link => {
            link.classList.remove('active');
        });

        // Добавляем класс "active" к текущему элементу
        this.classList.add('active');

        // Сохраняем активную вкладку в localStorage (опционально)
        localStorage.setItem('activePath', this.getAttribute('href'));
    });
});

    // Восстанавливаем активную вкладку при загрузке страницы (опционально)
    const activePath = localStorage.getItem('activePath');
    if (activePath) {
    navLinks.forEach(link => {
        if (link.getAttribute('href') === activePath) {
            link.classList.add('active');
        }
    });
}

const exitIcon = document.getElementById('exitIcon');

exitIcon.addEventListener('mouseenter', () => {
    exitIcon.src = '/img/exit-2.png'; // Изменяем изображение при наведении
});

exitIcon.addEventListener('mouseleave', () => {
    exitIcon.src = '/img/exit-1.png'; // Возвращаем исходное изображение
});

//Выпадающий список для страницы tasks

function toggleDropdown() {
    const dropdownContent = document.getElementById("dropdownContent");
    if (dropdownContent.style.display === "block") {
        dropdownContent.style.display = "none"; // Скрываем список
    } else {
        dropdownContent.style.display = "block"; // Показываем список
    }
}
//Форма добавления задачи - tasks.html
const openFormButton = document.getElementById('openFormButton');
const openFormButton2 = document.getElementById('openFormButton');
const closeFormButton = document.getElementById('closeFormButton');
const formPopup = document.getElementById('formPopup');
const overlay = document.createElement('div');
overlay.classList.add('overlay');
document.body.appendChild(overlay);

openFormButton.addEventListener('click', () => {
    formPopup.classList.add('active');
    overlay.classList.add('active');
});

openFormButton2.addEventListener('click', () => {
    formPopup.classList.add('active');
    overlay.classList.add('active');
});

closeFormButton.addEventListener('click', () => {
    formPopup.classList.remove('active');
    overlay.classList.remove('active');
});

overlay.addEventListener('click', () => {
    formPopup.classList.remove('active');
    overlay.classList.remove('active');
});



