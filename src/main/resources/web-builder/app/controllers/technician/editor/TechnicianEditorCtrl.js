import {Injector} from 'ngES6';
import angular from 'angular';
import './index.scss';

export default class TechnicianEditorCtrl extends Injector {
    static $inject = ['$scope', '$state', '$stateParams', 'Settings', 'TechnicianService'];
    static $template = require('./editor.html');

    constructor(...args) {
        super(...args);
        const {$scope, $stateParams, TechnicianService, Settings} = this.$injected;
        $scope.Settings = Settings;
        this.attachMethodsTo($scope);

        $scope.yearList = [
            {id: 0, name: '无'},
            {id: 1, name: '1年'},
            {id: 2, name: '2年'},
            {id: 3, name: '3年'},
            {id: 4, name: '4年'},
            {id: 5, name: '5年'},
            {id: 6, name: '6年'},
            {id: 7, name: '7年'},
            {id: 8, name: '8年'},
            {id: 9, name: '9年'},
            {id: 10, name: '10年'},
        ];

        $scope.starList = [
            {id: 0, name: '无'},
            {id: 1, name: '1星'},
            {id: 2, name: '2星'},
            {id: 3, name: '3星'},
            {id: 4, name: '4星'},
            {id: 5, name: '5星'},
        ];

        $scope.stateList = [
            {id: 1, name: '可接单'},
            {id: 2, name: '工作中'},
            {id: 3, name: '休息中'},
        ];

        TechnicianService.getV2Detail($stateParams.id).then(res => {
            if (res.data.status === true) {
                $scope.technician = res.data.message;
            }
        });
        $scope.uploadUrl = TechnicianService.uploadPhotoUrl;
    }

    onUploadedPhoto(data) {
        const {$scope, $uibModal} = this.$injected;
        if (!data.result) {
            $scope.message = data.message;
            $uibModal.open({
                size     : 'sm',
                scope    : $scope,
                animation: true,
                template : `
                    <div class="modal-header">
                        <h3 class="modal-title">提示消息</h3>
                    </div>
                    <div class="modal-body">
                        <b>{{message}}</b>
                    </div>
                    <div class="modal-footer">
                        <button class="btn btn-primary" type="button" ng-click="$close()">确定</button>
                    </div>`,
            });
        }
        return data.result ? data.data : '';
    }

    save() {
        const {$scope, $state, TechnicianService} = this.$injected;
        TechnicianService.update($scope.technician).then(res => {
            if (res.data.status === true) {
                let pCoop = $scope.$parent.technicians.find(c => c.id === res.data.message.id);
                if (pCoop) {
                    angular.extend(pCoop, res.data.message);
                }
                $state.go('^', {id: res.data.message.id});
            }
        });
    }
}