const path = require(`path`);

module.exports = {
    webpack: {
        alias: {
            '@': path.resolve(__dirname, 'src/'),
            '@Utils': path.resolve(__dirname, 'src/util'),
            '@Const': path.resolve(__dirname, 'src/util/const.js'),
            '@Components': path.resolve(__dirname, 'src/components'),
            '@Theme': path.resolve(__dirname, 'src/theme'),
            '@Error': path.resolve(__dirname, 'src/pages/error')
        }
    },
};