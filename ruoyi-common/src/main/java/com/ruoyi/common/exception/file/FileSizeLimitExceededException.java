package com.ruoyi.common.exception.file;

/**
 * File name size limit unusual categories
 * 
 * @author ruoyi
 */
public class FileSizeLimitExceededException extends FileException
{
    private static final long serialVersionUID = 1L;

    public FileSizeLimitExceededException(long defaultMaxSize)
    {
        super("upload.exceed.maxSize", new Object[] { defaultMaxSize });
    }
}
