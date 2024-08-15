const path = require(`path`);

module.exports = {
    webpack: {
        alias: {
            '@': path.resolve(__dirname, 'src/'),
            '@Utils': path.resolve(__dirname, 'src/utils'),
        }
    },
};