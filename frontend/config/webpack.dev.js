const helpers = require('./helpers');
const webpackMerge = require('webpack-merge');
const commonConfig = require('./webpack.common.js');

const DefinePlugin = require('webpack/lib/DefinePlugin');
const LoaderOptionsPlugin = require('webpack/lib/LoaderOptionsPlugin');

const ENV = process.env.ENV = process.env.NODE_ENV = 'development';

module.exports = webpackMerge(commonConfig, {
    devtool: 'cheap-module-source-map',
    output: {
        path: helpers.root('dist'),
        filename: '[name].bundle.js',
        sourceMapFilename: '[file].map',
        chunkFilename: '[id].chunk.js',
        library: 'ac_[name]',
        libraryTarget: 'var',
        publicPath: 'http://localhost:4200/'
    },
    module: {
        rules: [
            {
                test: /\.ts$/,
                use: [
                    {
                        loader: 'tslint-loader',
                        options: {
                            configFile: 'tslint.json',
                            emitErrors: true,
                            failOnHint: true
                        }
                    }
                ],
                exclude: [/\.spec\.ts$/]
            },
            {
                test: /\.scss$/,
                use: ['style-loader', 'css-loader', 'sass-loader'],
                include: [helpers.root('src/styles')]
            },
        ]
    },
    plugins: [
        new DefinePlugin({
            'ENV': JSON.stringify(ENV)
        }),
        new LoaderOptionsPlugin({
            debug: true
        })
    ],
    devServer: {
        port: 4200,
        host: 'localhost',
        historyApiFallback: true,
        overlay: true,
        watchOptions: {
            aggregateTimeout: 300,
            poll: 1000
        },
        proxy: {
            '/api': {
                target: 'http://localhost:8080'
            }
        }
    },
    node: {
        global: true,
        crypto: 'empty',
        process: true,
        module: false,
        clearImmediate: false,
        setImmediate: false
    }
});
