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
