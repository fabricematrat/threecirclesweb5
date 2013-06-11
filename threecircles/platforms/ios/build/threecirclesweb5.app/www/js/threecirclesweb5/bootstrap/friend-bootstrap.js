var threecirclesweb5 = threecirclesweb5 || {};

threecirclesweb5.loadfriend = (function () {
    threecirclesweb5.configuration.domain.push({
        name: 'friend',
        view: {
            'list': $('#section-list-friend'),
            'save': $('#submit-friend'),
            'add': $('#add-friend'),
            'show': $('a[id^="friend-list-"]'),
            'remove': $('#delete-friend')
        },
        options: {
            offline: true,
            eventPush: true
        }

    });
}());
