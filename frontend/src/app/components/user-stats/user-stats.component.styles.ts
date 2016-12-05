import {StyleSheet} from "aphrodite";
import {colors} from "../../styles/colors.styles";

export const styles = StyleSheet.create({
    section: {
        padding: '8px 0',
        borderBottom: `1px solid ${colors.borderColor}`,
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
