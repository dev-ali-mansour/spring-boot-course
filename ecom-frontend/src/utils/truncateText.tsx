const truncateText = (text: string, maxLength: number = 90): string => {
    return text.length > maxLength
        ? `${text.substring(0, maxLength)}...`
        : text;
};

export default truncateText;
