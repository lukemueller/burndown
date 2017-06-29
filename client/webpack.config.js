/* global require, module, __dirname */

const path = require('path');

module.exports = {
    entry: './src/index',
    output: {
        path: path.resolve(__dirname, 'dist'),
        filename: 'bundle.js'
    },
    module: {
        rules: [
            {
                test: /\.jsx?$/,
                exclude: [
                    path.resolve(__dirname, 'node_modules')
                ],
                loader: 'babel-loader',
                options: {
                    presets: ['es2015', 'react', 'stage-2']
                }
            }
        ]
    },
    externals: {
        cheerio: 'window',
        'react/addons': 'react',
        'react/lib/ExecutionEnvironment': 'react',
        'react/lib/ReactContext': 'react',
    },
    devtool: 'source-map',
    context: __dirname,
    //externals: ['react'],
    devServer: {
        proxy: {
            '/api': 'http://localhost:3000'
        },
        // contentBase: path.join(__dirname, 'public'),
        historyApiFallback: true,
        hot: true,
        https: false,
        noInfo: true
    }
};