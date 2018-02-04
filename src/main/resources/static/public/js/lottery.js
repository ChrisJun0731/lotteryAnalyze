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
                console.log('subscribe /topic/lotteryInfo');
                $scope.lotteryInfos = JSON.parse(result.body);
                $scope.$apply();
            });

            stompClient.subscribe('/topic/stat', function(result){
                console.log('subscribe /topic/stat');
                $scope.stats = JSON.parse(result.body);
                console.log($scope.stats);
                $scope.$apply();
            });

            stompClient.subscribe('/topic/ratio', function(result){
                console.log('subscribe /topic/ratio');
                $scope.ratioMap = JSON.parse(result.body);
                console.log($scope.ratioMap);
                $scope.$apply();
            });

        });

    };

    $scope.connect();

});