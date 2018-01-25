var app = angular.module('app', []);
app.controller('LotteryController', function($scope){

    $scope.lotteryInfos = [];
    var stompClient = null;

    $scope.connect = function(){
        var socket = new SockJS('/my-websocket');
        stompClient = Stomp.over(socket);
        stompClient.connect({}, function(frame){
            //订阅彩票消息
            stompClient.subscribe('/topic/lotteryInfo', function(result){
                console.log('callback is called');
                $scope.lotteryInfos = JSON.parse(result.body);
                $scope.$apply();
            });
        });
    };

    $scope.connect();

});