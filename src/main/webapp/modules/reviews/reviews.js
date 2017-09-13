'use strict';

var reviews = angular.module('myApp.reviews', ['ui.router']);
signUp.config(['$stateProvider', function($stateProvider) {

    $stateProvider.state('reviews', {
        url          : '/mainPage',    
        templateUrl  : 'modules/reviews/reviews.html',
        controller   : 'reviewsCtrl'
  });

}]);


reviews.controller('reviewsCtrl',reviewsCtrl);
reviewsCtrl.$inject = ['$scope' , '$http' , '$state'];

function reviewsCtrl ($scope , $http , $state) {
    
  $scope.country = "1";
  $scope.init = function(){
      $scope.getProvinces();
  };
  
  $scope.changeProvincy = function(){
      $scope.getProvinces();
  };
  
  $scope.getProvinces = function(){
     $.ajax({
        type        : "GET",
        url         : "GetProvinces", // Location of the service
        data        : {"country_id" : $scope.country}, //Data sent to server
        contentType : "application/json", // content type sent to server
        crossDomain : true,
        async       : false,
        success: function(data, success) {
            console.log("success getting the provinces");
            $scope.provinces = data;
            $scope.selected_province = data[0];
        }
    });      
  };
  
  $scope.init();

};





