"use strict";
var aphrodite_1 = require("aphrodite");
var colors_styles_1 = require("../../styles/colors.styles");
exports.styles = aphrodite_1.StyleSheet.create({
    section: {
        padding: '8px 0',
        borderBottom: "1px solid " + colors_styles_1.colors.borderColor,
    },
    userName: {
        fontSize: '1.4em',
        marginTop: 0,
        marginBottom: '4px',
    },
    userText: {
        display: 'block',
        marginBottom: '4px',
        lineHeight: 1,
    },
});
