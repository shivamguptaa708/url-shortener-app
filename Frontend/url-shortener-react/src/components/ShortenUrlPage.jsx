import { useEffect } from 'react';
import { useParams } from 'react-router-dom';

const ShortenUrlPage = () => {
  const { url } = useParams();

  useEffect(() => {
    if (url) {
      window.location.replace(
        `${import.meta.env.VITE_BACKEND_URL}/r/${url}`
      );
    }
  }, [url]);

  return null;
};

export default ShortenUrlPage;
