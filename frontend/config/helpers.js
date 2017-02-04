const path = require('path');
const ROOT = path.resolve(__dirname, '..');
const root = path.join.bind(path, ROOT);

function hasProcessFlag(flag) {
    return process.argv.join('').indexOf(flag) > -1;
}

function prod() {
    return process.env.NODE_ENV === 'production';
}

exports.hasProcessFlag = hasProcessFlag;
exports.root = root;
exports.prod = prod;
