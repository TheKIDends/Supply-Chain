const { createProxyMiddleware } = require('http-proxy-middleware');

module.exports = function(app) {
    app.use(
        '/api',
        createProxyMiddleware({
            target: 'http://192.168.0.106:8000',
            changeOrigin: true,
        })
    );

    app.use(
        '/storage',
        createProxyMiddleware({
            target: 'http://192.168.0.106:8000',
            changeOrigin: true,
        })
    );
};