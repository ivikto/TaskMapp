// Общий обработчик DOMContentLoaded
document.addEventListener('DOMContentLoaded', function() {
    console.log('DOM fully loaded and parsed');

    // Инициализация CSRF токена
    const csrfTokenMeta = document.querySelector('meta[name="_csrf"]');
    const csrfHeaderMeta = document.querySelector('meta[name="_csrf_header"]');
    let csrfToken = '';
    let csrfHeader = '';

    if (csrfTokenMeta && csrfHeaderMeta) {
        csrfToken = csrfTokenMeta.content;
        csrfHeader = csrfHeaderMeta.content;
    } else {
        console.warn('CSRF tokens not found!');
    }

    // 1. Обработчик для кнопки лайка
    function handleLikeClick(e) {
        const button = e.target.closest('.heart-btn');
        if (!button) return;

        e.preventDefault();
        e.stopPropagation();

        const container = button.closest('.like-container');
        if (!container) {
            console.error('Like container not found');
            return;
        }

        const newsId = container.dataset.newsId;
        const likesCount = button.querySelector('.likes-count');
        const heartIcon = button.querySelector('.heart-icon');

        if (!newsId || !likesCount || !heartIcon) {
            console.error('Required elements not found');
            return;
        }

        // Оптимистичное обновление UI
        const wasLiked = button.classList.contains('liked');
        button.disabled = true;
        heartIcon.textContent = wasLiked ? '♡' : '♥';
        likesCount.textContent = wasLiked
            ? parseInt(likesCount.textContent) - 1
            : parseInt(likesCount.textContent) + 1;

        fetch(`/news/${newsId}/like`, {
            method: 'POST',
            headers: {
                'X-Requested-With': 'XMLHttpRequest',
                'Content-Type': 'application/json',
                [csrfHeader]: csrfToken
            }
        })
            .then(response => {
                if (!response.ok) throw new Error('Network response was not ok');
                return response.json();
            })
            .then(data => {
                button.classList.toggle('liked', data.isLiked);
                likesCount.textContent = data.likes;
            })
            .catch(error => {
                console.error('Error:', error);
                heartIcon.textContent = wasLiked ? '♥' : '♡';
                likesCount.textContent = wasLiked
                    ? parseInt(likesCount.textContent) + 1
                    : parseInt(likesCount.textContent) - 1;
            })
            .finally(() => {
                button.disabled = false;
            });
    }

    // 2. Обработчики для навигационных ссылок
    const navLinks = document.querySelectorAll('.nav-link');
    if (navLinks.length > 0) {
        navLinks.forEach(link => {
            link.addEventListener('click', function(e) {
                e.preventDefault();
                navLinks.forEach(l => l.classList.remove('active'));
                this.classList.add('active');
                localStorage.setItem('activePath', this.getAttribute('href'));
                window.location.href = this.getAttribute('href');
            });
        });

        // Восстановление активной вкладки
        const activePath = localStorage.getItem('activePath');
        if (activePath) {
            navLinks.forEach(link => {
                if (link.getAttribute('href') === activePath) {
                    link.classList.add('active');
                }
            });
        }
    }

    // 3. Обработчик для иконки выхода
    const exitIcon = document.getElementById('exitIcon');
    if (exitIcon) {
        exitIcon.addEventListener('mouseenter', () => {
            exitIcon.src = '/img/exit-2.png';
        });
        exitIcon.addEventListener('mouseleave', () => {
            exitIcon.src = '/img/exit-1.png';
        });
    }

    // 4. Обработчики для формы
    const openFormButton = document.getElementById('openFormButton');
    const closeFormButton = document.getElementById('closeFormButton');
    const formPopup = document.getElementById('formPopup');

    if (openFormButton && closeFormButton && formPopup) {
        const overlay = document.createElement('div');
        overlay.classList.add('overlay');
        document.body.appendChild(overlay);

        openFormButton.addEventListener('click', () => {
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
    }

    // 5. Глобальные функции
    window.logoutRequest = function() {
        const form = document.createElement('form');
        form.method = 'POST';
        form.action = '/logout';

        const csrfInput = document.createElement('input');
        csrfInput.type = 'hidden';
        csrfInput.name = '_csrf';
        csrfInput.value = csrfToken;
        form.appendChild(csrfInput);

        document.body.appendChild(form);
        form.submit();
    };

    window.delRequest = function(taskId) {
        const form = document.createElement('form');
        form.method = 'POST';
        form.action = `/tasks/${taskId}/del`;

        const csrfInput = document.createElement('input');
        csrfInput.type = 'hidden';
        csrfInput.name = '_csrf';
        csrfInput.value = csrfToken;
        form.appendChild(csrfInput);

        document.body.appendChild(form);
        form.submit();
    };

    window.editRequest = function(taskId) {
        window.location.href = `/tasks/${taskId}/edit`;
    };

    // 6. Выпадающий список
    const dropdownToggle = document.querySelector('.dropdown-toggle');
    if (dropdownToggle) {
        dropdownToggle.addEventListener('click', function() {
            const dropdownContent = document.getElementById("dropdownContent");
            if (dropdownContent) {
                dropdownContent.style.display =
                    dropdownContent.style.display === "block" ? "none" : "block";
            }
        });
    }

    // Добавляем обработчик для кнопок лайка
    document.addEventListener('click', handleLikeClick);
});