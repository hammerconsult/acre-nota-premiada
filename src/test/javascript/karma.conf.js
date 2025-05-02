// Karma configuration
// http://karma-runner.github.io/0.10/config/configuration-file.html

module.exports = function (config) {
    config.set({
        // base path, that will be used to resolve files and exclude
        basePath: '../../',

        // testing framework to use (jasmine/mocha/qunit/...)
        frameworks: ['jasmine'],

        // list of files / patterns to load in the browser
        files: [
            // bower:js
            'main/webapp/bower_components/modernizr/modernizr.js',
            'main/webapp/bower_components/jquery/dist/jquery.js',
            'main/webapp/bower_components/bootstrap/dist/js/bootstrap.js',
            'main/webapp/bower_components/json3/lib/json3.js',
            'main/webapp/bower_components/angular/angular.js',
            'main/webapp/bower_components/angular-bootstrap/ui-bootstrap-tpls.js',
            'main/webapp/bower_components/angular-ui-router/release/angular-ui-router.js',
            'main/webapp/bower_components/angular-resource/angular-resource.js',
            'main/webapp/bower_components/angular-cookies/angular-cookies.js',
            'main/webapp/bower_components/angular-sanitize/angular-sanitize.js',
            'main/webapp/bower_components/angular-translate/angular-translate.js',
            'main/webapp/bower_components/angular-translate-storage-cookie/angular-translate-storage-cookie.js',
            'main/webapp/bower_components/angular-translate-loader-partial/angular-translate-loader-partial.js',
            'main/webapp/bower_components/angular-dynamic-locale/src/tmhDynamicLocale.js',
            'main/webapp/bower_components/angular-local-storage/dist/angular-local-storage.js',
            'main/webapp/bower_components/angular-cache-buster/angular-cache-buster.js',
            'main/webapp/bower_components/ngInfiniteScroll/build/ng-infinite-scroll.js',
            'main/webapp/bower_components/br-validations/releases/br-validations.js',
            'main/webapp/bower_components/string-mask/src/string-mask.js',
            'main/webapp/bower_components/angular-input-masks/angular-input-masks-standalone.min.js',
            'main/webapp/bower_components/angular-breadcrumb/release/angular-breadcrumb.js',
            'main/webapp/bower_components/angular-notify/dist/angular-notify.js',
            'main/webapp/bower_components/sweetalert/dist/sweetalert.min.js',
            'main/webapp/bower_components/angular-sweetalert/SweetAlert.js',
            'main/webapp/bower_components/ng-file-upload/ng-file-upload.js',
            'main/webapp/bower_components/blob-polyfill/Blob.js',
            'main/webapp/bower_components/file-saver.js/FileSaver.js',
            'main/webapp/bower_components/angular-file-saver/dist/angular-file-saver.bundle.js',
            'main/webapp/bower_components/br-masks/releases/br-masks.js',
            'main/webapp/bower_components/angular-br-filters/src/filters.js',
            'main/webapp/bower_components/summernote/dist/summernote.js',
            'main/webapp/bower_components/angular-summernote/dist/angular-summernote.js',
            'main/webapp/bower_components/ng-idle/angular-idle.js',
            'main/webapp/bower_components/pdfjs-dist/build/pdf.js',
            'main/webapp/bower_components/pdfjs-dist/build/pdf.worker.js',
            'main/webapp/bower_components/angular-pdf/dist/angular-pdf.js',
            'main/webapp/bower_components/ngImgCrop/compile/minified/ng-img-crop.js',
            'main/webapp/bower_components/file-saver/FileSaver.js',
            'main/webapp/bower_components/angularPrint/angularPrint.js',
            'main/webapp/bower_components/codemirror/lib/codemirror.js',
            'main/webapp/bower_components/angular-ui-codemirror/ui-codemirror.js',
            'main/webapp/bower_components/angular-recaptcha/release/angular-recaptcha.js',
            'main/webapp/bower_components/angular-mocks/angular-mocks.js',
            // endbower
            'main/webapp/app/app.js',
            'main/webapp/app/**/*.js',
            'main/webapp/scripts/components/**/*.{js,html}',
            'test/javascript/**/!(karma.conf).js'
        ],


        // list of files / patterns to exclude
        exclude: [],

        // web server port
        port: 9876,

        // level of logging
        // possible values: LOG_DISABLE || LOG_ERROR || LOG_WARN || LOG_INFO || LOG_DEBUG
        logLevel: config.LOG_INFO,

        // enable / disable watching file and executing tests whenever any file changes
        autoWatch: false,

        // Start these browsers, currently available:
        // - Chrome
        // - ChromeCanary
        // - Firefox
        // - Opera
        // - Safari (only Mac)
        // - PhantomJS
        // - IE (only Windows)
        browsers: ['PhantomJS'],

        // Continuous Integration mode
        // if true, it capture browsers, run tests and exit
        singleRun: false
    });
};
