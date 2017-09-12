'use strict';

var reviews = angular.module('myApp.reviews', ['ui.router']);
signUp.config(['$stateProvider', function($stateProvider) {

    $stateProvider.state('reviews', {
        url          : '/mainPage',    
        templateUrl  : 'modules/reviews/reviews.html',
        controller   : 'reviewsCtrl',
        controllerAs : 'vm'
  });

}]);


reviews.controller('reviewsCtrl',reviewsCtrl);
reviewsCtrl.$inject = ['$http'];

function reviewsCtrl ($http) {

};





