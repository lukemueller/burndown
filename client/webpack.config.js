/* global require, module, __dirname */

const path = require('path');
const webpack = require('webpack');

module.exports = {
    entry: path.resolve('./src/index'),
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
    devtool: 'source-map',
    context: __dirname,
    devServer: {
        port: 9000,
        proxy: {
            '**': {
                target: 'http://0.0.0.0:8080',
                secure: false
            }
        },
        // contentBase: path.join(__dirname, 'public'),
        historyApiFallback: true,
        hot: true,
        https: false,
        noInfo: true
    },
    plugins: [
        new webpack.DefinePlugin({
            BASE_URL: JSON.stringify('http://localhost:9000')
        })
    ]
};