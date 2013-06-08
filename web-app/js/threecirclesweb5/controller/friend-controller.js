var threecirclesweb5 = threecirclesweb5 || {};
threecirclesweb5.controller = threecirclesweb5.controller || {};

threecirclesweb5.controller.friendcontroller = function (feed, model, view, cfg) {
    var that = grails.mobile.mvc.controller(feed, model, view, cfg);

    //Place here your custom event
//    view.somethingButtonClicked.attach(function (item, context) {
//          // ....
//          // Notify the model
//          that.model.somethingHappened(data, context);
//    });

    return that;
};
