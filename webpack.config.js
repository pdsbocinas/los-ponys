/* jshint esversion: 6 */
const path = require('path')
const webpack = require('webpack')
const MiniCssExtractPlugin = require('mini-css-extract-plugin');
const WebpackAssetsManifest = require('webpack-assets-manifest');
const WebpackCleanupPlugin = require('webpack-cleanup-plugin');

const extractStyles = new MiniCssExtractPlugin({
  filename: '[name].css',
  chunkFilename: '[id].css',
});

module.exports = {
  mode: 'development',
  context: path.resolve(__dirname, './src/main/webapp/'),
  entry: {
    commons: [
      'babel-polyfill',
      'axios'
    ],
    countries: [
      './css/app.scss',
      './js/components/Countries/index.js'
    ],
    travel: [
      './js/components/Travel/index.js'
    ],
    login: [
      './js/components/Login/index.js'
    ],
    home: [
      './js/components/Home/index.js'
    ],
    modal: [
      './js/components/Modal-Travel/index.js'
    ],
    route: [
      './js/components/route/index.js'
    ],
    alojamientos: [
      './js/components/Alojamientos/index.js'
    ],
    posteo: [
      './js/components/Posteo/index.js'
    ]
  },
  output: {
    path: path.resolve(__dirname, './src/main/webapp/dist/'),
    filename: 'js/[name]-[hash].js',
    chunkFilename: 'js/[id]-[chunkhash].js',
    publicPath: '/'
  },
  module: {
    rules: [
      {
        test: /\.(js|jsx)$/,
        exclude: /node_modules/,
        use: {
          loader: "babel-loader"
        }
      },
      {
        test: /\.css$/,
        use: [
          {
            loader: MiniCssExtractPlugin.loader,
            options: {
              publicPath: '/',
              hmr: process.env.NODE_ENV === 'development',
            },
          },
          'css-loader'
        ]
      },
      {
        test: /\.scss$/,
        use: [
          {
            loader: MiniCssExtractPlugin.loader,
            options: {
              hmr: process.env.NODE_ENV === 'development',
            },
          },
          'css-loader'
        ]
      },
      {
        test: /\.(eot|svg|ttf|woff|woff2)$/,
        loader: 'file-loader?name=fonts/[name].[ext]'
      }
    ]
  },
  optimization: {
    splitChunks: {
      chunks: 'async',
      minSize: 30000,
      maxSize: 0,
      minChunks: 1,
      maxAsyncRequests: 5,
      maxInitialRequests: 3,
      automaticNameDelimiter: '~',
      automaticNameMaxLength: 30,
      name: true,
      cacheGroups: {
        vendors: {
          test: /[\\/]node_modules[\\/]/,
          priority: -10
        },
        default: {
          minChunks: 2,
          priority: -20,
          reuseExistingChunk: true
        }
      }
    }
  },
  plugins: [
    extractStyles,
    new WebpackAssetsManifest(),
    new WebpackCleanupPlugin()
  ]
}
