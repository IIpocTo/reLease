const helpers = require('./helpers');

const {CheckerPlugin} = require('awesome-typescript-loader');
const HtmlWebpackPlugin = require('html-webpack-plugin');
const ScriptExtHtmlWebpackPlugin = require('script-ext-html-webpack-plugin');
const NormalModuleReplacementPlugin = require('webpack/lib/NormalModuleReplacementPlugin');
const ContextReplacementPlugin = require('webpack/lib/ContextReplacementPlugin');
const CommonsChunkPlugin = require('webpack/lib/optimize/CommonsChunkPlugin');
const ProvidePlugin = require('webpack/lib/ProvidePlugin');

module.exports = {
    entry: {
        'polyfills': './src/polyfills.ts',
        'vendor': './src/vendor.ts',
        'main': './src/main.ts'
    },
    resolve: {
        extensions: ['.ts', '.js'],
        alias: {
            lodash: 'lodash-es'
        }
    },
    module: {
        rules: [
            {
                test: /\.ts$/,
                use: [
                    {
                        loader: '@angularclass/hmr-loader',
                        options: {
                            pretty: true,
                            prod: false
                        }
                    },
                    {
                        loader: 'ng-router-loader',
                        options: {
                            loader: 'async-import',
                            genDir: 'compiled'
                        }
                    },
                    {
                        loader: 'awesome-typescript-loader',
                    },
                    {
                        loader: 'angular2-template-loader'
                    }
                ],
                exclude: [/\.spec\.ts$/]
            },
            {
                test: /\.scss$/,
                use: [
                    'to-string-loader',
                    'css-loader',
                    'sass-loader'
                ],
                exclude: [helpers.root('src/styles')]
            },
            {
                test: /\.html$/,
                use: 'raw-loader',
                exclude: [helpers.root('src/index.html')]
            }
        ]
    },
    plugins: [
        new CheckerPlugin(),
        new ContextReplacementPlugin(
            // The (\\|\/) piece accounts for path separators in *nix and Windows
            /angular(\\|\/)core(\\|\/)(esm(\\|\/)src|src)(\\|\/)linker/,
            helpers.root('src')
        ),
        new CommonsChunkPlugin({
            name: 'polyfills',
            chunks: ['polyfills']
        }),
        new CommonsChunkPlugin({
            name: 'vendor',
            chunks: ['main']
        }),
        new CommonsChunkPlugin({
            name: ['polyfills', 'vendor'].reverse()
        }),

        new HtmlWebpackPlugin({
            template: 'src/index.html',
            chunksSortMode: 'dependency',
            inject: 'head'
        }),
        new ScriptExtHtmlWebpackPlugin({
           defaultAttribute: "defer"
        }),
        new ProvidePlugin({
            $: "jquery",
            jQuery: "jquery",
            "window.jQuery": "jquery",
            "window.Tether": 'tether',
            tether: 'tether',
            Tether: 'tether'
        })
    ],
    node: {
        global: true,
        crypto: 'empty',
        module: false,
        process: true,
        clearImmediate: false,
        setImmediate: false
    }
};
