const { createProxyMiddleware } = require('http-proxy-middleware');

module.exports = function(app) {
    app.use(
        '/api',
        createProxyMiddleware({
            target: 'http://192.168.1.19:8000',
            changeOrigin: true,
        })
    );

    app.use(
        '/storage',
        createProxyMiddleware({
            target: 'http://192.168.1.19:8000',
            changeOrigin: true,
        })
    );
};
