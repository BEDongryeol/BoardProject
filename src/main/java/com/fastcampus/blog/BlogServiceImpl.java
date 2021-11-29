package com.fastcampus.blog;

import com.fastcampus.post.PostVO;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BlogServiceImpl implements BlogService {

    final BlogDAO blogDAO;

    public BlogServiceImpl(BlogDAO blogDAO) {
        this.blogDAO = blogDAO;
    }


    @Override
    public BlogVO getBlog(BlogVO vo) {
        return blogDAO.getBlog(vo);
    }

    @Override
    public List<BlogVO> getBlogList(BlogVO vo) {
        return blogDAO.getBlogList(vo);
    }

    @Override
    public void registerBlog(BlogVO vo) {
        blogDAO.registerBlog(vo);
    }

    @Override
    public void updateBlog(BlogVO vo) {
        blogDAO.updateBlog(vo);
    }

    @Override
    public void deleteBlog(BlogVO vo) {
        blogDAO.deleteBlog(vo);
    }

}
