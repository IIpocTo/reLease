const helpers = require('./helpers');

const {CheckerPlugin} = require('awesome-typescript-loader');
const HtmlWebpackPlugin = require('html-webpack-plugin');
const ExtractTextPlugin = require("extract-text-webpack-plugin");
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
            lodash: 'lodash-es',
            aphrodite: 'aphrodite/no-important'
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
                            genDir: 'compiled',
                            aot: false
                        }
                    },
                    {
                        loader: 'awesome-typescript-loader',
                    },
                    {
                        loader: 'angular2-template-loader'
                    },
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
                test: /\.css$/,
                exclude: [helpers.root('src')],
                loader: ExtractTextPlugin.extract({
                    fallbackLoader: "style-loader",
                    loader: "css-loader"
                })
            },
            {test: /\.html$/, loader: 'raw-loader'}
        ]
    },
    plugins: [
        new HtmlWebpackPlugin({
            template: 'src/index.html',
            chunksSortMode: 'dependency'
        }),
        new CheckerPlugin(),
        new ContextReplacementPlugin(
            // The (\\|\/) piece accounts for path separators in *nix and Windows
            /angular(\\|\/)core(\\|\/)(esm(\\|\/)src|src)(\\|\/)linker/,
            helpers.root('src') // location of your src
        ),
        new CommonsChunkPlugin({
            name: ['polyfills', 'vendor'].reverse()
        }),
        new ExtractTextPlugin('[name].[chunkhash].css'),
        new ProvidePlugin({
            $: "jquery",
            jQuery: "jquery",
            "window.jQuery": "jquery",
            "window.Tether": 'tether',
            tether: 'tether',
            Tether: 'tether'
        })
    ],

    /*
     * Include polyfills or mocks for various node stuff
     * Description: Node configuration
     *
     * See: https://webpack.github.io/docs/configuration.html#node
     */
    node: {
        global: true,
        crypto: 'empty',
        module: false,
        process: true,
        clearImmediate: false,
        setImmediate: false
    }
};
